# StandardScene Simulation Configuration

This simulation scenario defines the structure, resources, and parameters for a hospital outpatient simulation model. Each scenario represents a different outpatient environment and contains the parameters for patient arrivals, examination distributions, department resources, and scheduling constraints.

---

## Overview
- **Class:** `StandardScene` (extends `Scenario`)  
- **Purpose:** Stores all parameters for a simulation run, including patient arrivals, exam orders, department resources, machines, and scheduling plans.  

### Simulation Periods
- **Warm-up period:** 5 days  
- **Service period:** 10 days  
- **Filling period:** 20 days  
- **Total simulation period:** 35 days  

### Patient Arrivals
- **Patient arrival rate:** Parameterized (`patient_arr_lambda`)  
- **Exam distribution:** Parameterized array (`exam_num_ratio`)  
- **Exam order structures:** `HashMap<Integer, OrderStructure>`  

---

## Departments and Machines

The simulation includes six departments with different numbers of machines and exam types:

| Department | Machines | Exam Items | 
|------------|---------|------------|
| CT         | 15      | 1          |
| DR         | 10      | 4          |
| MR         | 10      | 3          |
| US         | 15      | 4          |
| UC         | 5       | 1          |
| ES         | 5       | 3          |

---

## Machine Scheduling

Each machine has weekday and weekend plans specifying:

- Number of slots per day  
- Slot start and end times (minutes from day start)  
- Slot sizes (number of patients per slot)  

- **Max slots across scenario:** 5  

**Example for a CT machine:**  

**Weekday plan:** 3 slots per day  

| Slot ID | Start Time | End Time | Size |
|---------|------------|----------|------|
| 2       | 480        | 600      | 5    |
| 3       | 600        | 720      | 5    |
| 5       | 780        | 900      | 3    |

---

## Exam Items

Each department defines its exam items with:

- **ID**  
- **Department**  
- **Exam duration** (units)  
- **Time-dependent availability probabilities** (per slot)  

**Example for DR department:**

| Exam ID  | Duration | Probabilities (per slot)        |
|----------|----------|--------------------------------|
| E_DR01   | 1        | [0, 0.80, 0.65, 0.65, 0.65]   |
| E_DR02   | 2        | [0, 0.15, 0.25, 0.25, 0.25]   |
| E_DR03   | 3        | [0, 0.05, 0.05, 0.05, 0.05]   |
| E_DR04   | 4        | [0, 0.00, 0.05, 0.05, 0.05]   |

---

## Exam Order Structures

`OrderStructure` objects define combinations of exams a patient may require. Probabilities are based on real outpatient data.

**1-exam orders:**

| Department | Probability |
|------------|-------------|
| US         | 0.4019      |
| CT         | 0.3236      |
| MR         | 0.0734      |
| UC         | 0.0803      |
| DR         | 0.0648      |
| ES         | 0.0558      |

**2-exam orders (examples):**

| Combination | Probability |
|-------------|-------------|
| CT + US     | 0.2453      |
| UC + US     | 0.1870      |
| CT + MR     | 0.1598      |

**3-exam orders (examples):**

| Combination   | Probability |
|---------------|-------------|
| CT + UC + US  | 0.3686      |
| UC + MR + US  | 0.2777      |

**4-exam orders (examples):**

| Combination       | Probability |
|------------------|-------------|
| CT + MR + UC + US | 0.5744      |
| CT + ES + UC + US | 0.2891      |

---

## Usage

Instantiate the scenario with patient arrival rate and exam distribution:

```java
float[] exam_ratio = {0, 0.5f, 0.25f, 0.15f, 0.10f};
StandardScene scene = new StandardScene(443, exam_ratio);
scene.setUp();

Each department, machine, and exam item can be queried for scheduling and simulation logic.

This configuration allows the simulation of patient flows across multiple outpatient departments with realistic scheduling, machine availability, and exam order distributions.
