(ns iworked.store
  (:require [clojure.java.io :as io]
            [clj-time.format :as f]
            [iworked.date :as d])
  )

(defn date-to-string
  [date]
  (f/unparse d/ymd-formatter date))

(defn string-to-date
  [str]
  (f/parse d/ymd-formatter str))

(defn to-saveable
  [an-event]
  (update an-event :date date-to-string))

(defn from-saveable
  [an-event]
  (update an-event :date string-to-date))

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
    {}))

(defn load-events
  [loc]
  (map from-saveable (load-from loc)))

(defn save-events
  [loc data]
  (save-to loc (map to-saveable data)))

(defn add-event-to
  [current-events new-event]
  (assoc current-events (f/unparse d/ymd-formatter (:date new-event)) (:amount new-event)))



