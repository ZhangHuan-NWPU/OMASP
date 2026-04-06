StandardScene Simulation Configuration

This simulation scenario defines the structure, resources, and parameters for a hospital outpatient simulation model. Each scenario represents a different outpatient environment and contains the parameters for patient arrivals, examination distributions, department resources, and scheduling constraints.

Overview
Class: StandardScene (extends Scenario)
Purpose: Stores all parameters for a simulation run, including patient arrivals, exam orders, department resources, machines, and scheduling plans.
Simulation periods:
Warm-up period: 5 days
Service period: 10 days
Filling period: 20 days
Total simulation period: 35 days
Patient arrival rate: Parameterized (patient_arr_lambda)
Exam distribution: Parameterized array (exam_num_ratio)
Exam order structures: HashMap<Integer, OrderStructure>
Departments and Machines

The simulation includes six departments with different numbers of machines and exam types.

Department	Machines	Exam Items	Machine Speeds
CT	15	1	1.0
DR	10	4	1.315
MR	10	3	2.311
US	15	4	1.618
UC	5	1	1.0
ES	5	3	1.398
Machine Scheduling
Each machine has weekday and weekend plans specifying:
Number of slots per day
Slot start and end times
Slot sizes (number of patients per slot)
max_slot across the scenario: 5

Example for CT machine:

Weekday plan: 3 slots per day
- IDs: 2, 3, 5
- Start times: 480, 600, 780
- End times: 600, 720, 900
- Slot sizes: 5, 5, 3
Exam Items

Each department defines its exam items with:

ID
Department
Exam duration (units)
Time-dependent availability probabilities (per slot)

Example for DR department:

E_DR01: duration=1, probabilities=[0, 0.80, 0.65, 0.65, 0.65]
E_DR02: duration=2, probabilities=[0, 0.15, 0.25, 0.25, 0.25]
E_DR03: duration=3, probabilities=[0, 0.05, 0.05, 0.05, 0.05]
E_DR04: duration=4, probabilities=[0, 0.00, 0.05, 0.05, 0.05]
Exam Order Structures

OrderStructure objects define combinations of exams a patient may require. Probabilities are based on real outpatient data.

1-exam orders:
US: 0.4019
CT: 0.3236
MR: 0.0734
UC: 0.0803
DR: 0.0648
ES: 0.0558
2-exam orders: Examples
CT + US: 0.2453
UC + US: 0.1870
CT + MR: 0.1598
3-exam orders: Examples
CT + UC + US: 0.3686
UC + MR + US: 0.2777
4-exam orders: Examples
CT + MR + UC + US: 0.5744
CT + ES + UC + US: 0.2891
Usage
Instantiate the scenario with patient arrival rate and exam distribution:
float[] exam_ratio = {0, 0.5f, 0.25f, 0.15f, 0.10f};
StandardScene scene = new StandardScene(443, exam_ratio);
scene.setUp();
Access departments, machines, and orders:
ArrayList<Department> departments = scene.getDept_list();
HashMap<Integer, OrderStructure> orders = scene.getOrder_structure_list();
Each department, machine, and exam item can be queried for scheduling and simulation logic.

This configuration allows the simulation of patient flows across multiple outpatient departments with realistic scheduling, machine availability, and exam order distributions.
