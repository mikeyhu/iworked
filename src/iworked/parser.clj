(ns iworked.parser
  (:require [clj-time.core :as t]
            [clj-time.periodic :as p]
            [clj-time.format :as f]
            [clojure.string :as str])
  )

(def day-formatter (f/formatter "EEEE"))

(defn start-of-day
  [date]
  (t/date-midnight (t/year date) (t/month date) (t/day date)))

(defn today [] (start-of-day (t/now)))
(defn last-week [] (take 7 (p/periodic-seq (today) (t/days -1))))

(defn worked
  [date amount]
  {:date date :amount amount})

(defn parse-day
  [arg]
  (first
    (filter #(= (str/lower-case (f/unparse day-formatter %)) (str/lower-case arg)) (last-week))))

(defn parse-date
  [command]
  (case command
    "today" (today)
    "yesterday" (t/minus (today) (t/days 1))
    (parse-day command)
    ))

(defn parse-word
  [arg]
  (worked (parse-date arg) 1))
