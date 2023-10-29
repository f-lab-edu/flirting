rootProject.name = "flirting"

include(":app-service-api", ":app-point-consumer", "app-recommendation")
include(":domain", ":domain:domain-rds", ":domain:domain-redis")
include(":gateway")
include(":core")
include(":notification")