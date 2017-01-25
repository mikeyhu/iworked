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

(defn yesterday [] (t/minus (today) (t/days 1)))

(defn last-week [] (take 7 (p/periodic-seq (today) (t/days -1))))

(defn months [] (take 12 (p/periodic-seq (today) (t/months -1))))