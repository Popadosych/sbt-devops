#!/bin/bash

PIDFILE="disk_monitor.pid"

MONITOR_INTERVAL=600

monitor() {
    CURRENT_DATE=$(date +"%Y-%m-%d")
    FILENAME="${CURRENT_DATE}.csv"
    
    echo "timestamp;disk_usage;free_inodes" >> "$FILENAME"
    
    while true; do
        TIMESTAMP=$(date +"%Y-%m-%d_%H:%M:%S")
        
        DISK_USAGE=$(df / | awk 'NR==2 {print $5}' | sed 's/%//')
        FREE_INODES=$(df -i / | awk 'NR==2 {print $4}')
        
        NEW_DATE=$(date +"%Y-%m-%d")
        if [ "$NEW_DATE" != "$CURRENT_DATE" ]; then
            CURRENT_DATE=$NEW_DATE  
            FILENAME="${CURRENT_DATE}.csv"
        fi

        echo "$TIMESTAMP;$DISK_USAGE;$FREE_INODES" >> "$FILENAME"
        
        sleep $MONITOR_INTERVAL
    done
}

start() {
    if [ -f "$PIDFILE" ]; then
        echo "Monitor is running with PID $(cat $PIDFILE)"
        exit 1
    else
        monitor &
        echo $! > "$PIDFILE"
    fi
}

status() {
    if [ -f "$PIDFILE" ]; then
        echo "Monitor is running with PID $(cat $PIDFILE)"
    else
        echo "Monitor is not running"
    fi
}

stop() {
    if [ -f "$PIDFILE" ]; then
        PID=$(cat "$PIDFILE")
        kill $PID
        rm "$PIDFILE"
    else
        echo "Monitor is not running"
    fi
}

case "$1" in
    START)
        start
        ;;
    STOP)
        stop
        ;;
    STATUS)
        status
        ;;
    *)
        echo "Invalid argument"
        exit 1
        ;;
esac
