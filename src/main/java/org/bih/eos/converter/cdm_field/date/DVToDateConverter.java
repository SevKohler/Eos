package org.bih.eos.converter.cdm_field.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.nedap.archie.rm.composition.Action;
import com.nedap.archie.rm.composition.EventContext;
import com.nedap.archie.rm.composition.Observation;
import com.nedap.archie.rm.datastructures.Element;
import com.nedap.archie.rm.datastructures.IntervalEvent;
import com.nedap.archie.rm.datastructures.PointEvent;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDate;
import com.nedap.archie.rm.datavalues.quantity.datetime.DvDateTime;

public class DVToDateConverter {

	private static final Logger LOG = LoggerFactory.getLogger(DVToDateConverter.class);

	public Optional<Date> convert(Object objectAtPath)  {

		Class objectClassAtPath = objectAtPath.getClass();
		if (objectClassAtPath.equals(PointEvent.class)) {
			PointEvent pointEvent = (PointEvent) objectAtPath;
			return parseDvDateTime(pointEvent.getTime().getValue());
		} else if (objectClassAtPath.equals(IntervalEvent.class)) {
			return getIntervalEventStart((IntervalEvent) objectAtPath);
		} else if (objectClassAtPath.equals(Observation.class)) {
			Observation observation = (Observation) objectAtPath;
			return parseDvDateTime(observation.getData().getOrigin().getValue());
		} else if (objectClassAtPath.equals(Element.class) && ((Element) objectAtPath).getValue().getClass().equals(DvDateTime.class)) {
			DvDateTime dvDateTime = (DvDateTime) ((Element) objectAtPath).getValue();
			return parseDvDateTime(dvDateTime.getValue());
		} else if (objectClassAtPath.equals(Element.class) && ((Element) objectAtPath).getValue().getClass().equals(DvDate.class)) {
			DvDate dvDate = (DvDate) ((Element) objectAtPath).getValue();
			return parseDvDate(dvDate.getValue());
		} else if (objectClassAtPath.equals(Action.class)) {
			Action action = (Action) objectAtPath;
			return parseDvDateTime(action.getTime().getValue());
		} else if (objectClassAtPath.equals(EventContext.class)) {
			EventContext eventContext = (EventContext) objectAtPath;
			return parseDvDateTime(eventContext.getStartTime().getValue());
		}
		LOG.warn("A Date type was found that was currently not supported or can not be mapped ! Mapping will be processed if optional otherwise the conversion is skipped and the next one processed");
		return Optional.empty();

	}

	public Optional<Date> convertEndTime(Object objectAtPath) {
	    if (objectAtPath instanceof PointEvent pointEvent) {
	        return parseDvDateTime(pointEvent.getTime().getValue());
	    } else if (objectAtPath instanceof IntervalEvent intervalEvent) {
	        return parseDvDateTime(intervalEvent.getTime().getValue());
	    } else if (objectAtPath instanceof Observation observation) {
	        return parseDvDateTime(observation.getData().getOrigin().getValue());
	    } else if (objectAtPath instanceof Element element) {
	        Object value = element.getValue();
	        if (value instanceof DvDateTime dvDateTime) {
	            return parseDvDateTime(dvDateTime.getValue());
	        } else if (value instanceof DvDate dvDate) {
	            return parseDvDate(dvDate.getValue());
	        }
	    } else if (objectAtPath instanceof Action action) {
	        return parseDvDateTime(action.getTime().getValue());
	    } else if (objectAtPath instanceof EventContext eventContext) {
	        if (eventContext.getEndTime() != null) {
	            return parseDvDateTime(eventContext.getEndTime().getValue());
	        } else {
	            return parseDvDateTime(eventContext.getStartTime().getValue());
	        }
	    }
		LOG.warn("A Date type was found that was currently not supported or can not be mapped ! Mapping will be processed if optional otherwise the conversion is skipped and the next one processed");
		return Optional.empty();
	}

	private Optional<Date> parseDvDate(Temporal dvDate) {
		if (dvDate == null) {
			return Optional.empty();
		} else {
			ZoneId defaultZoneId = ZoneId.systemDefault();
			LocalDate dateTime = LocalDate.parse(dvDate.toString());
			return Optional.of(Date.from(dateTime.atStartOfDay(defaultZoneId).toInstant()));
		}
	}

	private Optional<Date> getIntervalEventStart(IntervalEvent intervalEvent) {
		if (intervalEvent.getWidth() != null) {
			TemporalAmount end = intervalEvent.getWidth().getValue();
			Optional<Date> start = parseDvDateTime(intervalEvent.getTime().getValue());
			return parseDvDateTime(end.subtractFrom(start.get().toInstant()));
		} else {
			return parseDvDateTime(intervalEvent.getTime().getValue());
		}
	}

        /**
	 * Parses a {@link TemporalAccessor} from an openEHR DvDateTime into a {@link Date}.
	 * <p>
	 * This method is designed to be robust against different date-time representations from openEHR. It handles two primary cases
	 * by inspecting the provided {@link TemporalAccessor} before parsing:
	 * <ul>
	 *     <li><b>With Time Zone Offset:</b> If the input contains an offset (e.g., "2022-02-03T04:05:06+02:00"),
	 *     it is parsed directly into an {@link OffsetDateTime} to preserve the exact moment in time.</li>
	 *     <li><b>Without Time Zone Offset:</b> If the input lacks an offset (e.g., "2022-02-03T04:05:06"), it is treated as a
	 *     {@link LocalDateTime}. To convert this to a specific instant (which {@link Date} represents), the system's default time zone is applied.</li>
	 * </ul>
	 * This avoids {@link DateTimeParseException} for valid local date-times and ensures consistent conversion.
	 * @param dvDateTime The {@link TemporalAccessor} to parse, typically from a {@link DvDateTime}.
	 * @return An {@link Optional} containing the parsed {@link Date}, or an empty Optional if parsing fails or the input is partial/null.
	 */
	public Optional<Date> parseDvDateTime(TemporalAccessor dvDateTime) {
		if (dvDateTime == null || !dvDateTime.isSupported(ChronoField.DAY_OF_MONTH)) {
			LOG.warn("Date was empty, or partial (e.g. Day missing), therefore Date is returned empty.");
			return Optional.empty();
		} 

		try {
			if (dvDateTime.isSupported(ChronoField.OFFSET_SECONDS)) {
				// Has an offset, parse as OffsetDateTime
				OffsetDateTime dateTime = OffsetDateTime.from(dvDateTime);
				return Optional.of(Date.from(dateTime.toInstant()));
			} else {
				// No offset, parse as LocalDateTime and use system default zone
				LocalDateTime localDateTime = LocalDateTime.from(dvDateTime);
				return Optional.of(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
			}
		} catch (DateTimeParseException e) {
	        LOG.warn("Could not parse date {}: {}. Skipping but continuing.", dvDateTime, e.getMessage());
	        return Optional.empty();
		}

	}
}
