(ns iworked.event
  (:require [clj-time.format :as f]))

(def ymd-formatter (f/formatter "yyyyMMdd"))

(defn event
  [date amount]
  {:date date :amount amount})

;unused at the moment

(defn date-to-string
  [date]
  (f/unparse ymd-formatter date))

(defn string-to-date
  [str]
  (f/parse ymd-formatter str))

(defn to-saveable
  [an-event]
  (update an-event :date date-to-string))

(defn from-saveable
  [an-event]
  (update an-event :date string-to-date))
