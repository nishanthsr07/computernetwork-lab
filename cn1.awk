
BEGIN {
    count = 0;
}

{
    if ($1 == "d") {
        count++;
    }
}

END {
    printf("Total packets dropped due to congestion: %d\n", count);
}
