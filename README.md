# iWorked

Record and view the days you worked.

Data is stored as a clojure data structure in a file `~/.iworked.cljdata`.

# Building

```bash
lein uberjar
```

# Running

```bash
# Run via lein
lein run all monday

# or via the bash script provided, once an uberjar has been built.
iworked.sh all monday

# all day today
iworked.sh

# all day yesterday
iworked.sh yesterday

# all day on the most recent Tuesday just gone
iworked.sh tuesday

# on christmas day
iworked.sh 20161225

# half day yesterday
iworked.sh half yesterday

# quarter day on January 1st
iworked.sh quarter 20170101


# Display what days you worked in the current month
iworked.sh show

# Display what days you worked in the most recent January
iworked.sh show january
```