import time
import sys

time.sleep(2)

# Array di 100 valori interi da 100 a 1
values = list(range(100, 0, -1))

# Funzione che stampa un valore dall'array ogni 1 secondo
def sonar():
	print(98)
	sys.stdout.flush()
	time.sleep(5)
	print(50)
	sys.stdout.flush()
	time.sleep(5)
	print(0)
	sys.stdout.flush()
	exit(-1)

if __name__ == "__main__":
    sonar()

    
