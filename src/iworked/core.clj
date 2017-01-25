(ns iworked.core
  (:require [iworked.parser :as p]
            [iworked.store :as s]
            [iworked.report :as r])
  (:gen-class))

(def location (str (System/getProperty "user.dir") "/.iworked.cljdata"))

(defn -main
  [& args]
  (let
    [data (s/load-from location)]
    (if (= (first args) "show")
      (r/display-report (r/choose-report (rest args) data))
      (s/save-to location
                 (s/add-event-to data (p/parse-all args))))))

