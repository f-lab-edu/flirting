rootProject.name = "flirting"

include(":app:app-service-api")
include(":domain", ":domain:domain-rds", ":domain:domain-redis")
include(":gateway")
include(":core")
include(":notification")