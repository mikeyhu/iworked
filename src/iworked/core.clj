(ns iworked.core
  (:require [iworked.parser :as p]
            [iworked.store :as s]
            [iworked.report :as r]
            [clj-time.core :as t])
  (:gen-class))

(def location (str (System/getProperty "user.dir") "/.iworked.cljdata"))

(defn -main
  [& args]
  (let
    [data (s/load-from location)]
    (if (= (first args) "show")
      (r/display-days-in-month (r/days-in-month (t/now)))
      (s/save-to location
                 (s/add-event-to data (p/parse-all args))))))

