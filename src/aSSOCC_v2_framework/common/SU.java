package aSSOCC_v2_framework.common;

import java.util.ArrayList;
import java.util.Random;

import aSSOCC_v2_framework.agents.Person;
import repast.simphony.context.Context;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.util.SimUtilities;

/**
 * The Simulation Utility class, can return IDs for new instances and contains the context
 * @author Maarten
 *
 */
		
public final class SU {

	private static final Random random = new Random();
	private static Context<Object> masterContext = null;
	
	private static int newPersonId = 0;
	private static int newGatheringPointId = 0;
	
	
	/**
	 * This returns a new Id. ++ is used after the variable to make sure
	 * the current GatheringPointId is returned
	 * @return a new unused id for a GatheringPoint
	 */
	public static int getNewGatheringPointId() {
		return newGatheringPointId++;
	}

	public static void resetGatheringPointId() {
		newGatheringPointId = 0;
	}
	
	/**
	 * This returns a new Id. ++ is used after the variable to make sure
	 * the current PersonId is returned
	 * @return a new unused id for a Person
	 */
	public static int getNewPersonId() {
		return newPersonId++;
	}

	public static void resetPersonId() {
		newPersonId = 0;
	}
	
	public static Person getPersonById(int id) {
		
		ArrayList<Person> persons = SU.getObjectsAllRandom(Person.class);
		for (Person person : persons) {
			if (person.getId() == id) {
				return person;
			}
		}
		return null;
	}
	
	public static void setContext(Context<Object> masterContext) {
		SU.masterContext = masterContext;
	}
	
	public static boolean getRandomBoolean() {
		return random.nextBoolean();
	}
	
	/**
	 * Gets master context since I don't use the sub-contexts
	 * @return Context the master context
	 */
	public static Context<Object> getContext() {

		if (masterContext == null)  {
			Logger.logError("SimUtils.getContext(): context returned null");
		}
		return masterContext;
	}
	
	@SuppressWarnings("unchecked")
	public static Grid<Object> getGrid() {
		Grid<Object> grid = (Grid<Object>) getContext().getProjection(Constants.ID_GRID);
		if (grid == null)  {
			Logger.logError("SimUtils.getGrid(): grid returned null");
		}
		return grid;
	}
	
	@SuppressWarnings("unchecked")
	public static ContinuousSpace<Object> getSpace() {
		ContinuousSpace<Object> space = (ContinuousSpace<Object>) getContext().getProjection(Constants.ID_SPACE);
		if (space == null)  {
			Logger.logError("SimUtils.getSpace(): space returned null");
		}
		return space;
	}
	
	/**
	 * Retrieves all the objects within the master context based on the given class.
	 * @param clazz (e.g. use as input Human.class)
	 * @return an ArrayList of objects from the given class
	 */
	public static <T> ArrayList<T> getObjectsAll(Class<T> clazz) {
		
		@SuppressWarnings("unchecked")
		final Iterable<T> objects = (Iterable<T>) getContext().getObjects(clazz);
		final ArrayList<T> objectList = new ArrayList<T>();
		for (final T object : objects) {
			objectList.add(object);
		}
		return objectList;
	}
	
	/**
	 * Same as getObjectsAll but uses SimUtilities.shuffle to randomize
	 * the ArrayList of objects
	 * @param clazz (e.g. use as input Human.class)
	 * @return an ArrayList of objects from the given class
	 */
	public static <T> ArrayList<T> getObjectsAllRandom(Class<T> clazz) {
		
		ArrayList<T> objectList = getObjectsAll(clazz);
		SimUtilities.shuffle(objectList, RandomHelper.getUniform());
		return objectList;
	}
	
	/**
	 * Retrieves all the objects, excluding the given object, within the master context based
	 * on the given class.
	 * @param clazz (e.g. use as input Human.class)
	 * @return an ArrayList of objects from the given class
	 */
	public static <T> ArrayList<T> getObjectsAllExcluded(Class<T> clazz, Object excludedObject) {
		
		@SuppressWarnings("unchecked")
		final Iterable<T> objects = (Iterable<T>) getContext().getObjects(clazz);
		final ArrayList<T> objectList = new ArrayList<T>();
		for (final T object : objects) {
			if (object != excludedObject) {
				objectList.add(object);
			}
		}
		return objectList;
	}
	
	/**
	 * Same as getObjectsAllExcluded but uses SimUtilities.shuffle to randomize
	 * the ArrayList of objects
	 * @param clazz (e.g. use as input Human.class)
	 * @return an ArrayList of objects from the given class
	 */
	public static <T> ArrayList<T> getObjectsAllRandomExcluded(Class<T> clazz, Object excludedObject) {
		
		ArrayList<T> objectList = getObjectsAllExcluded(clazz, excludedObject);
		SimUtilities.shuffle(objectList, RandomHelper.getUniform());
		return objectList;
	}
}