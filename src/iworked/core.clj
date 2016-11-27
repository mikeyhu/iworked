(ns iworked.core
  (:require [iworked.parser :as p]
            [iworked.store :as s])
  (:gen-class))

(def location (str (System/getProperty "user.dir") ".iworked.cljdata"))

(defn -main
  [& args]
  (let
    [data (s/load-from location)]
    (s/save-to location
               (s/add-event-to data (p/parse-all args)))))

