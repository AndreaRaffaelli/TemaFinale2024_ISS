import time
import sys

time.sleep(2)

# Array di 100 valori interi da 100 a 1
values = list(range(100, 0, -1))

# Funzione che stampa un valore dall'array ogni 1 secondo
def sonar():
    for value in values:
        time.sleep(0.00001)
        print(value)
        sys.stdout.flush()
        time.sleep(0.25)

if __name__ == "__main__":
    sonar()

    
