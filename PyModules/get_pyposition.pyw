import pyautogui as pygui

xPos, yPos = pygui.position()
print(xPos, yPos, sep="-", end="")