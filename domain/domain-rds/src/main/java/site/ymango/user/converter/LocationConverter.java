package site.ymango.user.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.locationtech.jts.geom.*;
import site.ymango.user.model.Location;

@Converter
public class LocationConverter implements AttributeConverter<Location, Point> {
  @Override
  public Point convertToDatabaseColumn(Location attribute) {
    GeometryFactory geometryFactory = new GeometryFactory();
    return geometryFactory.createPoint(new Coordinate(attribute.x(), attribute.y()));
  }

  @Override
  public Location convertToEntityAttribute(Point dbData) {
    return new Location(dbData.getX(), dbData.getY());
  }
}
