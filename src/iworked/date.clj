(ns iworked.date
  (:require [clj-time.format :as f]
            [clj-time.core :as t]
            [clj-time.periodic :as p]))

(def day-formatter (f/formatter "EEEE"))
(def month-formatter (f/formatter "MMMM"))
(def ymd-formatter (f/formatter "yyyyMMdd"))
(def report-formatter (f/formatter "EEE dd MMM yyyy"))

(defn start-of-day
  [date]
  (t/date-midnight (t/year date) (t/month date) (t/day date)))

(defn today [] (start-of-day (t/now)))

(defn last-week [] (take 7 (p/periodic-seq (today) (t/days -1))))

(defn months [] (take 12 (p/periodic-seq (today) (t/months -1))))


(apply slide word)
(def word "that")
(defn slide
  [a b & rest]
  (let [ab (str a b)]
    (if rest
      (cons ab (apply slide (apply str b rest)))
      [ab]
      ))
  )