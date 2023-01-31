import sys
import time
import pyautogui as pygui

# Set variables with position of target textbox.
tgtX = int(sys.argv[1])
tgtY = int(sys.argv[2])

delay = int(sys.argv[3]) // 1000 # Delay before start spamming
frequency = int(sys.argv[4]) / 1000 # Spamming frequency
timer_set = int(sys.argv[5]) // 1000 # Transform miliseconds into seconds and set operating time.

time.sleep(delay)

pygui.moveTo(tgtX, tgtY)
tgtX, tgtY = pygui.position()
start_timestamp = int(time.time()) # Store timestamp of start time.

while True:
    curX, curY = pygui.position()

    if tgtX != curX or tgtY != curY: # If mouse pointer is moved after start this program...
        break # End program.
    elif int(time.time()) - start_timestamp > timer_set and timer_set != 0: # If time is over...
        break # End program.

    # Proceed spamming.
    pygui.click(tgtX, tgtY)
    pygui.hotkey('ctrl', 'v')
    pygui.press("enter")

    time.sleep(frequency)

# Copyright 2023. Benedictus Park(benedictuspark1220@gmail.com) all rights reserved.