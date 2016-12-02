(ns iworked.parser
  (:require [clj-time.core :as t]
            [clj-time.periodic :as p]
            [clj-time.format :as f]
            [iworked.util :as u]
            [iworked.date :as d]
            [clojure.string :as str]))

(defn parse-today-yesterday
  [word]
  (case
    word
    "today" (d/today)
    "yesterday" (t/minus (d/today) (t/days 1))
    nil))

(defn parse-day
  [word]
  (first
    (filter #(= (str/lower-case (f/unparse d/day-formatter %)) (str/lower-case word)) (d/last-week))))

(defn parse-ymd
  [word]
  (try
    (f/parse d/ymd-formatter word)
    (catch Exception _ nil)))

(defn parse-amounts
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
           parse-ymd]))

(defn parse-time
  [word]
  {:date (u/first-non-nil word parsers)})

(defn parse-word
  [word]
  (or
    (parse-amounts word)
    (parse-time word)))

(defn parse-all
  [args]
  (reduce
    merge
    {:date (d/today) :amount 1} (map parse-word args)))