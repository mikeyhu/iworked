(ns iworked.report
  (:require [clj-time.core :as t]
            [clj-time.periodic :as p]
            [iworked.date :as d]))

(defn days-in-month
  [month]
  (filter #(not (t/after? % (d/today)))
          (take (t/number-of-days-in-the-month month)
                (p/periodic-seq
                  (t/first-day-of-the-month month) (t/days 1)))))

(defn display-days-in-month
  [days]
  (doseq [i days] (println i)))

