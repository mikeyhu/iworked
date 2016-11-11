(ns iworked.parser-test
  (:require [clojure.test :refer :all]
            [iworked.parser :refer :all]
            [clj-time.core :as t]))

(deftest worked-contains-date-and-amount
  (testing
    (let
      [date (t/now)
       amount 1
       worked (worked date amount)]
      (is (= date (:date worked)))
      (is (= amount (:amount worked)))
      )))

(deftest parser-understands-today
  (testing
    (let
      [input "today"
       midnight (t/today-at-midnight)]
      (is (= midnight (:date (parse-word input)))))))

(deftest parser-understands-yesterday
  (testing
    (let
      [yesterday-midnight (t/minus (t/today-at-midnight) (t/days 1))]
      (is (= yesterday-midnight (:date (parse-word "yesterday")))))))

(deftest parser-understands-Monday
  (testing
    (with-redefs [today (fn [] (t/date-time 2016 12 25))]
      (let
        [monday (t/date-time 2016 12 19)]
        (is (= monday (:date (parse-word "Monday"))))))))

(deftest parser-understands-lowercase-days
  (testing
    (is (not (nil? (:date (parse-word "monday")))))))

(deftest parser-returns-nil-if-unparseable
  (testing
    (is (nil? (:date (parse-word "Midweekday"))))))