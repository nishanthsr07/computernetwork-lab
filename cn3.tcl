
# Create simulator
set ns [new Simulator]

# Create trace files
set tf [open lab3.tr w]
$ns trace-all $tf

set nf [open lab3.nam w]
$ns namtrace-all $nf

# Create four nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]

# Set packet colors
$ns color 1 red
$ns color 2 blue

# Create Ethernet LAN
$ns make-lan "$n0 $n1 $n2 $n3" 10Mb 10ms LL Queue/DropTail Mac/802_3

# First TCP connection: n0 to n3
set tcp0 [new Agent/TCP]
$ns attach-agent $n0 $tcp0

set ftp0 [new Application/FTP]
$ftp0 attach-agent $tcp0

set sink3 [new Agent/TCPSink]
$ns attach-agent $n3 $sink3

$ns connect $tcp0 $sink3

# Second TCP connection: n2 to n1
set tcp2 [new Agent/TCP]
$ns attach-agent $n2 $tcp2

set ftp2 [new Application/FTP]
$ftp2 attach-agent $tcp2

set sink1 [new Agent/TCPSink]
$ns attach-agent $n1 $sink1

$ns connect $tcp2 $sink1

# Set flow classes
$tcp0 set class_ 1
$tcp2 set class_ 2

# Trace congestion window of first TCP
set file1 [open file1.tr w]
$tcp0 attach $file1
$tcp0 trace cwnd_
$tcp0 set maxcwnd_ 10

# Trace congestion window of second TCP
set file2 [open file2.tr w]
$tcp2 attach $file2
$tcp2 trace cwnd_
$tcp2 set maxcwnd_ 10

# Finish procedure
proc finish {} {
    global ns nf tf file1 file2

    $ns flush-trace

    close $tf
    close $nf
    close $file1
    close $file2

    exec nam lab3.nam &
    exit 0
}

# Start and stop first FTP traffic
$ns at 0.1 "$ftp0 start"
$ns at 1.5 "$ftp0 stop"
$ns at 2.0 "$ftp0 start"
$ns at 3.0 "$ftp0 stop"

# Start and stop second FTP traffic
$ns at 0.2 "$ftp2 start"
$ns at 2.0 "$ftp2 stop"
$ns at 2.5 "$ftp2 start"
$ns at 4.0 "$ftp2 stop"

# End simulation
$ns at 5.0 "finish"

# Run simulation
$ns run
