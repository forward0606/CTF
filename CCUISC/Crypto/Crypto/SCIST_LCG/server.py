import sys
from Crypto.Util.number import getPrime
from secret import FLAG


def challenge():
    m = getPrime(128)
    nonce, a, c = [getPrime(64) for _ in range(3)]
    problem = []
    for _ in range(16):
        problem.append(nonce)
        nonce = (a * nonce + c) % m

    problem = ",".join(map(str, problem))
    print(f"Problem: {problem}")
    if nonce != int(input("Answer: ")):
        print("Wrong Answer.")
        return

    print(f"Yes, here is your flag: {FLAG}")
    print("[FLAG] LCG", file=sys.stderr)


def main():
    print("Welcome to LCG server, guess random number.")
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
    
