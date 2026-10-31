
# Program 1: Point-to-Point Network

# Create simulator
set ns [new Simulator]

# Create trace file
set tf [open lab1.tr w]
$ns trace-all $tf

# Create NAM file
set nf [open lab1.nam w]
$ns namtrace-all $nf

# Create four nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]

# Set packet colors
$ns color 1 Red
$ns color 2 Blue

# Set node labels
$n0 label "Source UDP0"
$n1 label "Source UDP1"
$n2 label "Router"
$n3 label "Destination"

# Create duplex links
$ns duplex-link $n0 $n2 10Mb 300ms DropTail
$ns duplex-link $n1 $n2 10Mb 300ms DropTail
$ns duplex-link $n2 $n3 1Mb 300ms DropTail

# Set queue limits
$ns queue-limit $n0 $n2 10
$ns queue-limit $n1 $n2 10
$ns queue-limit $n2 $n3 5

# Create UDP agent 0
set udp0 [new Agent/UDP]
$ns attach-agent $n0 $udp0

# Create CBR traffic 0
set cbr0 [new Application/Traffic/CBR]
$cbr0 attach-agent $udp0

# Create UDP agent 1
set udp1 [new Agent/UDP]
$ns attach-agent $n1 $udp1

# Create CBR traffic 1
set cbr1 [new Application/Traffic/CBR]
$cbr1 attach-agent $udp1

# Create destination agent
set null3 [new Agent/Null]
$ns attach-agent $n3 $null3

# Set packet colors
$udp0 set class_ 1
$udp1 set class_ 2

# Connect agents
$ns connect $udp0 $null3
$ns connect $udp1 $null3

# Set packet size
$cbr0 set packetSize_ 500
$cbr1 set packetSize_ 500

# Set packet transmission interval
$cbr0 set interval_ 0.005
$cbr1 set interval_ 0.005

# Finish procedure
proc finish {} {
    global ns nf tf

    $ns flush-trace

    close $tf
    close $nf

    exec nam lab1.nam &
    exit 0
}

# Start traffic
$ns at 0.1 "$cbr0 start"
$ns at 0.1 "$cbr1 start"

# Stop simulation
$ns at 10.0 "finish"

# Run simulation
$ns run
