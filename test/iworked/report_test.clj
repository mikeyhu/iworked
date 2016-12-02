(ns iworked.report-test
  (:require [clojure.test :refer :all]
            [iworked.report :refer :all]
            [clj-time.core :as t]
            [iworked.date :as d]))

(deftest finds-days-in-month
  (testing
    (let [date (t/date-time 2016 01)
          first-of-month (t/date-time 2016 01 01)
          last-of-month (t/date-time 2016 01 31)
          days (days-in-month date)]
      (is (= 31 (count days)))
      (is (= first-of-month (first days)))
      (is (= last-of-month (last days))))))

(deftest only-shows-days-in-the-past
  (testing
    (with-redefs [d/today (fn [] (t/date-time 2016 12 25))]
      (let [date (t/date-time 2016 12)
            days (days-in-month date)]
        (is (= (t/date-time 2016 12 25) (last days)))
        ))))