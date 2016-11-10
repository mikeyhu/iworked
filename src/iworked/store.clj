(ns iworked.store
  (:require [clojure.java.io :as io])
  )

(defn save-to
  [location data]
  (with-open [w (clojure.java.io/writer location)]
    (binding [*out* w]
      (pr data))))

(defn file-exists
  [location]
  (.exists (io/as-file location)))

(defn load-from
  [location]
  (if (file-exists location)
    (with-open [r (java.io.PushbackReader. (clojure.java.io/reader location))]
      (binding [*read-eval* false]
        (read r)))
    []))


