(ns iworked.parser
  (:require [clj-time.format :as f]
            [iworked.util :as u]
            [iworked.date :as d]
            [clojure.string :as str]))

(defn parse-today-yesterday
  [word]
  (case
    word
    "today" (d/today)
    "yesterday" (d/yesterday)
    nil))

(defn parse-day "parse day names to be one of the last 7 days"
  [word]
  (first
    (filter #(= (str/lower-case (f/unparse d/day-formatter %)) (str/lower-case word)) (d/last-week))))

(defn parse-month "parse month names to be one of the last 12 months"
  [word]
  (if word
    (first
      (filter #(= (str/lower-case (f/unparse d/month-formatter %)) (str/lower-case word)) (d/months)))))

(defn parse-ymd "parse date in yyyyMMdd format"
  [word]
  (try
    (f/parse d/ymd-formatter word)
    (catch Exception _ nil)))

(defn parse-amounts "parse all, half or quarter of day amounts"
  [word]
  (case
    word
    "all" {:amount 1}
    "half" {:amount 0.5}
    "quarter" {:amount 0.25}
    nil))

(def parsers
  (u/seq1 [parse-today-yesterday
           parse-day
           parse-ymd
           parse-amounts]))

(defn parse-time "try parsers in order to try to get non null value"
  [word]
  {:date (u/first-non-nil word parsers)})

(defn parse-word "try amount parser then time parser"
  [word]
  (or
    (parse-amounts word)
    (parse-time word)))

(defn parse-all
  [args]
  (reduce
    merge
    {:date (d/today) :amount 1} (map parse-word args)))