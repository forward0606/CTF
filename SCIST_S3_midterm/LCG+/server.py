import sys

from Crypto.Util.number import getPrime, inverse

from secret import FLAG


def challenge():
    a = getPrime(64)
    c = getPrime(64)
    m = getPrime(128)

    for _ in range(16):
        nonce = int(input("Enter your nonce: "))
        if nonce < (1 << 64) or nonce > (1 << 128):
            print("Wrong nonce size")
            return

        for _ in range(3):
            nonce = (a * nonce + c ** 2) % m
        print(f"Here's your nonce: {nonce}")

    answer = int(input("Give me the answer: "))
    if answer == c ** 2 * inverse(a - 1, m) % m:
        print(f"Yes, here is your flag: {FLAG}")
        print("[FLAG] LCG+", file=sys.stderr)
        return

    print("Try again!")


def main():
    while True:
        print("> challenge")
        print("> server.py")
        print("> exit")
        cmd = input("> Command: ")
        if cmd == "exit":
            sys.exit(1)
        elif cmd == "challenge":
            challenge()
            sys.exit(1)
        elif cmd == "server.py":
            print(open("./server.py", "r").read())
        else:
            print("Bad hacker")


if __name__ == "__main__":
    try:
        main()
    except EOFError:
        sys.exit(1)
