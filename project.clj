(defproject hs_test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.3.1"]
                 [org.postgresql/postgresql "42.2.12"]
                 [environ "1.2.0"]
                 [ring-cors "0.1.13"]
                 [ring/ring-mock "0.4.0"]
                 [honeysql "1.0.444"]
                 [nilenso/honeysql-postgres "0.2.6"]
                 [migratus "1.2.8"]
                 [org.slf4j/slf4j-log4j12 "1.7.30"]
                 [org.clojure/java.jdbc "0.7.11"]]
  :plugins [[lein-ring "0.12.5"]
            [lein-environ "1.2.0"]]
  :ring {:handler hs-test.core/app}

  :profiles {:dev           [:project/dev :profiles/dev]
             :test          [:project/test :profiles/test]
             :profiles/dev  {}
             :profiles/test {}
             :project/dev   {:source-paths ["src"]
                             :dependencies [[javax.servlet/servlet-api "2.5"]]
                             :plugins      [[lein-auto "0.1.3"]]}
             :project/test  {:dependencies [[javax.servlet/servlet-api "2.5"]]}})