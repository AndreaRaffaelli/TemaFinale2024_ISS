import time

# Array di 100 valori interi da 100 a 1
values = list(range(100, 0, -1))

# Funzione che stampa un valore dall'array ogni 1 secondo
def sonar():
    for value in values:
        print(value)
        time.sleep(0.5)

if __name__ == "__main__":
    sonar()
