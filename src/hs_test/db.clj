(ns hs-test.db
  (:require [clj-postgresql.core :as pg]
            [environ.core :refer [env]]))

(def db (atom nil))

(defn db-connect []
  (reset! db (pg/pool :host (env :dbhost)
            :user (env :dbuser)
            :dbname (env :dbname)
            :password (env :dbpassword))))
