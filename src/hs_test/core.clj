(ns hs-test.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [hs-test.handlers.patient :refer [patient]]
            [clj-postgresql.core :as pg]
            [ring.middleware.json :as middleware]
            [hs-test.db :refer [db-connect]]))

(db-connect)

(defroutes app-routes
  (-> (routes patient
      (route/not-found "Not Found"))))

(def app
  (-> app-routes
      (middleware/wrap-json-body {:keywords? true})
      middleware/wrap-json-response
      (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))

; lein ring server-headless
; lein repl
