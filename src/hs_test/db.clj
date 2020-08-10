(ns hs-test.db
  (:require [clj-postgresql.core :as pg]
            [environ.core :refer [env]]))

(def db (atom nil))

(defn db-connect []
  (reset! db (pg/pool :host (env :db-host)
            :user (env :db-user)
            :dbname (env :db-name)
            :password (env :db-password))))
