(ns iworked.date
  (:require [clj-time.format :as f]
            [clj-time.core :as t]
            [clj-time.periodic :as p]))

(def day-formatter (f/formatter "EEEE"))
(def ymd-formatter (f/formatter "yyyyMMdd"))

(defn start-of-day
  [date]
  (t/date-midnight (t/year date) (t/month date) (t/day date)))

(defn today [] (start-of-day (t/now)))

(defn last-week [] (take 7 (p/periodic-seq (today) (t/days -1))))
