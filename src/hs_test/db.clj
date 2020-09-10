(ns hs-test.db
  (:require [environ.core :refer [env]]
            [migratus.core :as migratus]))

(def db {:host     (env :dbhost)
         :user     (env :dbuser)
         :dbname   (env :dbname)
         :dbtype   (env :dbtype)
         :port     (env :dbport)
         :password (env :dbpassword)})

(def config {:store                :database
             :migration-dir        "migrations/"
             :migration-table-name "migration"
             :db                   db})

(defn make-migrations []
  (migratus/migrate config))

(defn reset-migrations []
  (migratus/reset config))
