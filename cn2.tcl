
# Create simulator
set ns [new Simulator]

# Create trace files
set tf [open lab2.tr w]
$ns trace-all $tf

set nf [open lab2.nam w]
$ns namtrace-all $nf

# Create network nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]
set n6 [$ns node]

# Give labels to nodes
$n0 label "Ping0"
$n4 label "Ping4"
$n5 label "Ping5"
$n6 label "Ping6"
$n2 label "Router"

# Set packet colors
$ns color 1 red
$ns color 2 green

# Create duplex links
$ns duplex-link $n0 $n2 100Mb 300ms DropTail
$ns duplex-link $n2 $n6 1Mb 300ms DropTail
$ns duplex-link $n5 $n2 100Mb 300ms DropTail
$ns duplex-link $n2 $n4 1Mb 300ms DropTail
$ns duplex-link $n3 $n2 1Mb 300ms DropTail
$ns duplex-link $n1 $n2 1Mb 300ms DropTail

# Set queue limits
$ns queue-limit $n0 $n2 5
$ns queue-limit $n2 $n6 2
$ns queue-limit $n2 $n4 3
$ns queue-limit $n5 $n2 5

# Create Ping agents
set ping0 [new Agent/Ping]
$ns attach-agent $n0 $ping0

set ping4 [new Agent/Ping]
$ns attach-agent $n4 $ping4

set ping5 [new Agent/Ping]
$ns attach-agent $n5 $ping5

set ping6 [new Agent/Ping]
$ns attach-agent $n6 $ping6

# Configure packet sizes and intervals
$ping0 set packetSize_ 50000
$ping0 set interval_ 0.0001

$ping5 set packetSize_ 60000
$ping5 set interval_ 0.00001

# Set flow classes
$ping0 set class_ 1
$ping5 set class_ 2

# Connect Ping agents
$ns connect $ping0 $ping4
$ns connect $ping5 $ping6

# Display received ping replies
Agent/Ping instproc recv {from rtt} {
    $self instvar node_
    puts "Node [$node_ id] received reply from $from with RTT $rtt"
}

# Finish procedure
proc finish {} {
    global ns nf tf

    $ns flush-trace
    close $tf
    close $nf

    exec nam lab2.nam &
    exit 0
}

# Simulate link failure and recovery
$ns rtmodel-at 0.9 down $n2 $n6
$ns rtmodel-at 1.5 up $n2 $n6

# Schedule Ping0 messages
for {set t 0.1} {$t <= 1.8} {set t [expr $t + 0.1]} {
    $ns at $t "$ping0 send"
}

# Schedule Ping5 messages
for {set t 0.1} {$t <= 1.8} {set t [expr $t + 0.1]} {
    $ns at $t "$ping5 send"
}

# Stop simulation
$ns at 5.0 "finish"

# Run simulation
$ns run
