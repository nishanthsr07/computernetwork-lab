
BEGIN {
    print "Time\tCongestion Window"
}

{
    if ($6 == "cwnd_") {
        printf("%f\t%f\n", $1, $7)
    }
}

END {
    print "DONE"
}
