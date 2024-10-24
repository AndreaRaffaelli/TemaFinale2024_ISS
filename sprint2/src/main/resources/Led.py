# File: LedDevice.py
import RPi.GPIO as GPIO
import sys
import time  # Importa la libreria per i ritardi

GPIO.setmode(GPIO.BCM)
GPIO.setup(25, GPIO.OUT)

def blink_led(blink_duration=5, blink_interval=0.5):

    end_time = time.time() + blink_duration
    while time.time() < end_time:
        GPIO.output(25, GPIO.HIGH)
        time.sleep(blink_interval)  # Attesa prima di spegnere il LED
        GPIO.output(25, GPIO.LOW)
        time.sleep(blink_interval)  # Attesa prima di riaccendere il LED

for line in sys.stdin:
    print("LedDevice receives:", line)
    try:
        if 'on' in line:
            GPIO.output(25, GPIO.HIGH)  
        elif 'off' in line:
            GPIO.output(25, GPIO.LOW)  
        elif 'blink' in line:
            # blink_led(blink_duration=5, blink_interval=0.5)  # Lampeggia il LED per 5 secondi 
            # Versione semplice dove lampeggia due volte
            GPIO.output(25, GPIO.HIGH) 
            GPIO.output(25, GPIO.LOW)
            GPIO.output(25, GPIO.HIGH) 
            GPIO.output(25, GPIO.LOW) 
            print("Unknown command:", line)
    except Exception as e:
        print("LedDevice | An exception occurred:", e)

