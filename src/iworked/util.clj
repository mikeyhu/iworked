(ns iworked.util)

(defn seq1 "dechunks a lazy sequence"
  [s]
  (lazy-seq
    (when-let [[x] (seq s)]
      (cons x (seq1 (rest s))))))

(defn first-non-nil "passes the arg to each function in turn, returns first non nil"
  [arg funcs]
  (some identity (map #(% arg) funcs)))
