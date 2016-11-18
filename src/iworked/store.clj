(ns iworked.store
  (:require [clojure.java.io :as io]
            [clj-time.format :as f]
            [iworked.event :as e])
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
    {}))

(def ymd-formatter (f/formatter "yyyyMMdd"))

(defn load-events
  [loc]
  (map e/from-saveable (load-from loc)))

(defn save-events
  [loc data]
  (save-to loc (map e/to-saveable data)))

(defn add-event-to
  [current-events new-event]
  (assoc current-events (f/unparse ymd-formatter (:date new-event)) (:amount new-event)))



