import time
import sys

time.sleep(2)

# Array di 100 valori interi da 100 a 1
values = list(range(100, 0, -1))

# Funzione che stampa un valore dall'array ogni 1 secondo
def sonar():
	print(90)
	sys.stdout.flush()
	time.sleep(5)

if __name__ == "__main__":
    time.sleep(5)
    while True:
        sonar()

    
