(ns iworked.date
  (:require [clj-time.format :as f]
            [clj-time.core :as t]))

(def day-formatter (f/formatter "EEEE"))
(def ymd-formatter (f/formatter "yyyyMMdd"))

(defn start-of-day
  [date]
  (t/date-midnight (t/year date) (t/month date) (t/day date)))
