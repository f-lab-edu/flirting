package site.ymango.match.model;

public record AcceptMatchEvent(
        long userId,
        long targetProfileId
) {

}
