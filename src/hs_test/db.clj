(ns hs-test.db
  (:require [clj-postgresql.core :as pg]))

(def db (atom nil))

(defn db-connect []
  (reset! db (pg/pool :host "localhost"
            :user "hs_test"
            :dbname "hs_test"
            :password "hs_test")))
