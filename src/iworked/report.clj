(ns iworked.report
  (:require [clj-time.core :as t]
            [clj-time.periodic :as p]
            [clojure.string :as s]
            [iworked.date :as d]
            [clj-time.format :as f]
            [clansi :as c]
            [clj-time.predicates :as pr]
            [iworked.parser :as parser]))

(defn days-in-month
  [month]
  (filter #(not (t/after? % (d/today)))
          (take (t/number-of-days-in-the-month month)
                (p/periodic-seq
                  (t/first-day-of-the-month (d/start-of-day month)) (t/days 1)))))


(defn display-amount
  [amount]
  (case
    amount
    1 "all day"
    0.5 "half the day"
    0.25 "quarter of the day"
    nil "did not work"))

(defn choose-color
  [row]
  (case
    (:amount row)
    1 :green
    0.5 :cyan
    0.25 :blue
    (if (pr/weekday? (:date row))
      :red
      :white)))

(defn render-row
  [row]
  (c/style
    (s/join " " [(f/unparse d/report-formatter (:date row))
                 (display-amount (:amount row))])
    (choose-color row))
  )

(defn display-report
  [report]
  (doseq [r report]
    (println
      (render-row r))))

(defn create-report
  [days data]
  (letfn
    [(lookup [d] (get data (f/unparse d/ymd-formatter d)))]
    (map #(hash-map :date % :amount (lookup %)) days))
  )

(defn choose-report
  [args data]
  (let [date
        (or
          (parser/parse-month (first args))
          (d/today))]
    (create-report (days-in-month date) data))
  )
