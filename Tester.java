import java.util.ArrayList;
import java.util.List;

class Asset {
    private String assetId;
    private String assetName;
    private String assetExpiry;

    public Asset(String assetId, String assetName, String assetExpiry) {
        this.assetId = assetId;
        this.assetName = assetName;
        this.assetExpiry = assetExpiry;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetExpiry() {
        return assetExpiry;
    }

    public void setAssetExpiry(String assetExpiry) {
        this.assetExpiry = assetExpiry;
    }
}

class Resources {
    public static int getMonth(String month) {
        switch (month) {
            case "Jan":
                return 1;
            case "Feb":
                return 2;
            case "Mar":
                return 3;
            case "Apr":
                return 4;
            case "May":
                return 5;
            case "Jun":
                return 6;
            case "Jul":
                return 7;
            case "Aug":
                return 8;
            case "Sep":
                return 9;
            case "Oct":
                return 10;
            case "Nov":
                return 11;
            case "Dec":
                return 12;
            default:
                return 0;
        }
    }
}

class InvalidAssetsException extends Exception {
    public InvalidAssetsException(String message) {
        super(message);
    }
}

class InvalidExperienceException extends Exception {
    public InvalidExperienceException(String message) {
        super(message);
    }
}

abstract class Employee {
    protected static int contractIdCounter = 10000;
    protected static int permanentIdCounter = 10000;

    protected String employeeName;
    protected String employeeId;
    protected double salary;

    public Employee(String employeeName) {
        this.employeeName = employeeName;
    }

    public abstract void calculateSalary(float factor);

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = (salary > 0) ? salary : 0;
    }
}

class ContractEmployee extends Employee {
    private double wagePerHour;

    public ContractEmployee(String employeeName, double wagePerHour) {
        super(employeeName);
        this.wagePerHour = wagePerHour;
    }

    @Override
    public void calculateSalary(float hoursWorked) {
        if (hoursWorked >= 190) {
            salary = wagePerHour * hoursWorked;
        } else {
            double deduction = 0.5 * wagePerHour * (190 - hoursWorked);
            salary = Math.round((wagePerHour * hoursWorked - deduction));
        }
    }
}

class PermanentEmployee extends Employee {
    private double basicPay;
    private String[] salaryComponents;
    private Asset[] assets;
    private float experience;

    public PermanentEmployee(String employeeName, double basicPay, String[] salaryComponents, Asset[] assets) {
        super(employeeName);
        this.basicPay = basicPay;
        this.salaryComponents = salaryComponents;
        this.assets = assets;
    }
    // Getters and setters
    public double getBasicPay() {
        return basicPay;
    }

    public void setBasicPay(double basicPay) {
        this.basicPay = basicPay;
    }

    public String[] getSalaryComponents() {
        return salaryComponents;
    }

    public void setSalaryComponents(String[] salaryComponents) {
        this.salaryComponents = salaryComponents;
    }

    public Asset[] getAssets() {
        return assets;
    }

    public void setAssets(Asset[] assets) {
        this.assets = assets;
    }

    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    @Override
    public void calculateSalary(float factor) {
        experience = factor;
        try {
            double bonus = calculateBonus(experience);
            double daComponent = 0;
            double hraComponent = 0;
            for (String component : salaryComponents) {
                String[] parts = component.split("-");
                if (parts[0].equals("DA")) {
                    daComponent = Double.parseDouble(parts[1]) / 100 * basicPay;
                } else if (parts[0].equals("HRA")) {
                    hraComponent = Double.parseDouble(parts[1]) / 100 * basicPay;
                }
            }
            salary = basicPay + daComponent + hraComponent + bonus;
        } catch (InvalidExperienceException e) {
            salary = basicPay;
        }
    }

    private double calculateBonus(float experience) throws InvalidExperienceException {
        if (experience < 2.5) {
            throw new InvalidExperienceException("A minimum of 2.5 years is required for bonus!");
        }
        // Calculate bonus based on experience
        return 0; // Placeholder for bonus calculation
    }

    public Asset[] getAssetsByDate(String lastDate) throws InvalidAssetsException {
        List<Asset> validAssets = new ArrayList<>();
        // Logic to filter assets by expiry date
        return validAssets.toArray(new Asset[0]);
    }
}

class Admin {
    public void generateSalarySlip(Employee[] employees, float[] salaryFactor) {
        for (int i = 0; i < employees.length; i++) {
            employees[i].calculateSalary(salaryFactor[i]);
        }
    }

    public int generateAssetsReport(Employee[] employees, String lastDate) {
        int totalAssets = 0;
        for (Employee employee : employees) {
            if (employee instanceof PermanentEmployee) {
                try {
                    Asset[] assets = ((PermanentEmployee) employee).getAssetsByDate(lastDate);
                    totalAssets += assets.length;
                } catch (InvalidAssetsException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return totalAssets;
    }

    public String[] generateAssetsReport(Employee[] employees, char assetCategory) {
        List<String> assetIds = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee instanceof PermanentEmployee) {
                Asset[] assets = ((PermanentEmployee) employee).getAssets();
                for (Asset asset : assets) {
                    if (asset.getAssetId().toUpperCase().charAt(0) == Character.toUpperCase(assetCategory)) {
                        assetIds.add(asset.getAssetId());
                    }
                }
            }
        }
        return assetIds.toArray(new String[0]);
    }
}

class Tester{
    public static void main(String[] args) {
    	Admin admin = new Admin();
    	
    	Asset asset1 = null;
    	Asset asset2 = null;
    	Asset asset3 = null;
    	Asset asset4 = null;
    	Asset asset5 = null;
    	Asset asset6 = null;
    	Asset asset7 = null;
    	Asset asset8 = null;
    	Asset asset9 = null;
    	Asset asset10 = null;
    	
    	PermanentEmployee permanentEmployee1 = null;
    	PermanentEmployee permanentEmployee2 = null;
    	PermanentEmployee permanentEmployee3 = null;
    	PermanentEmployee permanentEmployee4 = null;
    	PermanentEmployee permanentEmployee5 = null;
		
		ContractEmployee contractEmployee1 = null;
		ContractEmployee contractEmployee2 = null;
		
		Employee[] employees = null;
		float[] salaryFactor = null;
		
		try {
			asset1 = new Asset("DSK-876761L", "Dell-Desktop", "2020-Dec-01");
	    	asset2 = new Asset("DSK-876762L", "Acer-Desktop", "2021-Mar-31");
	    	asset3 = new Asset("DSK-876763L", "Dell-Desktop", "2022-Jun-12");
	    	asset4 = new Asset("LTP-987123H", "Dell-Laptop", "2021-Dec-31");
	    	asset5 = new Asset("LTP-987124h", "Dell-Laptop", "2021-Sep-20");
	    	asset6 = new Asset("LTP-987125L", "HP-Laptop", "2022-Oct-25");
	    	asset7 = new Asset("LTP-987126l", "HP-Laptop", "2021-Oct-02");
	    	asset8 = new Asset("IPH-110110h", "VoIP", "2021-Dec-12");
	    	asset9 = new Asset("IPH-1101201h", "VoIP", "2020-Dec-31");
	    	asset10 = new Asset("IPH-110130h", "VoIP", "2020-Nov-30");
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Details of all available assets");
		System.out.println();
		
		try {
			Asset[] assets = { asset1, asset2, asset3, asset4, asset5, asset6, asset7, asset8, asset9, asset10 };
			int counter = 1;
			for (Asset asset : assets) {
				System.out.println("Details of asset"+counter++);
				System.out.println("\tAsset Id: "+asset.getAssetId());
				System.out.println("\tAsset Name: "+asset.getAssetName());
				System.out.println("\tAsset Valid Till: "+asset.getAssetExpiry());
				System.out.println();
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Correcting all the invalid assetIds");
		System.out.println();
		
		try {
			asset9.setAssetId("IPH-110120h");
			System.out.println("Details of asset9");
			System.out.println("\tAsset Id: "+asset9.getAssetId());
			System.out.println("\tAsset Name: "+asset9.getAssetName());
			System.out.println("\tAsset Valid Till: "+asset9.getAssetExpiry());
			System.out.println();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			permanentEmployee1 = new PermanentEmployee("Roger Fed", 15500.0, new String[] {"DA-50","HRA-40"}, new Asset[] {asset1, asset10});
			permanentEmployee2 = new PermanentEmployee("Serena W", 14000.0, new String[] {"DA-40","HRA-40"}, new Asset[] {asset6, asset9});
			permanentEmployee3 = new PermanentEmployee("James Peter", 18500.0, new String[] {"DA-50","HRA-45"}, new Asset[] {asset4});
			permanentEmployee4 = new PermanentEmployee("Catherine Maria", 20000.0, new String[] {"DA-50","HRA-45"}, new Asset[] {asset2, asset5});
			permanentEmployee5 = new PermanentEmployee("Jobin Nick", 21000.0, new String[] {"DA-50","HRA-50"}, null);

			contractEmployee1 = new ContractEmployee("Rafael N", 70);			
			contractEmployee2 = new ContractEmployee("Ricky Neol", 72.5);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println();
		System.out.println("Initiating salary calculation...");
		
		try {
			employees = new Employee[] { permanentEmployee1, permanentEmployee2, permanentEmployee3, permanentEmployee4, permanentEmployee5,
					contractEmployee1, contractEmployee2 };
			salaryFactor = new float[] { 3.9f, 2.3f, 4f, 8.1f, 12.5f, 189, 211 };
			
			admin.generateSalarySlip(employees, salaryFactor);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		permanentEmployee5.setSalary(-1);
		
		System.out.println();
		System.out.println("Details of employees");
		System.out.println();
		
		try {
			int pCounter = 1, cCounter = 1;
			for (Employee employee : employees) {
				if (employee instanceof PermanentEmployee) {
					PermanentEmployee permanentEmployee = (PermanentEmployee) employee;
					System.out.println("Details of permanentEmployee"+pCounter++);
					System.out.println("\tEmployee Id: "+permanentEmployee.getEmployeeId());
					System.out.println("\tEmployee Name: "+permanentEmployee.getEmployeeName());
					System.out.println("\tSalary: "+permanentEmployee.getSalary());	
					System.out.println("\tExperience: "+permanentEmployee.getExperience());	
					System.out.print("\tAssets Allocated: ");
					if (permanentEmployee.getAssets() != null) {
						for (Asset asset : permanentEmployee.getAssets()) {
							System.out.print(asset.getAssetId()+" ");
						}
						System.out.println();
					}
					else
						System.out.println("No assets allocated!");
				}
				else {
					System.out.println("Details of contractEmployee"+cCounter++);
					System.out.println("\tEmployee Id: "+employee.getEmployeeId());
					System.out.println("\tEmployee Name: "+employee.getEmployeeName());
					System.out.println("\tSalary: "+employee.getSalary());
				}
				System.out.println();
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println();	
		System.out.println("Reports");
		System.out.println();
		
		try {
			
			employees = new Employee[] { permanentEmployee1, permanentEmployee2, permanentEmployee3, permanentEmployee4,
					contractEmployee1, contractEmployee2 };
			
			int assetCount = admin.generateAssetsReport(employees, "2021-Dec-31");
			if (assetCount >= 0)
				System.out.println("Number of allocated assets expiring on or before 2021-Dec-31: "+assetCount);
			else
				System.out.println("Sorry, report cannot be generated!");
			
			System.out.println();
			
			assetCount = admin.generateAssetsReport(employees, "2020-Sep-30");
			if (assetCount >= 0)
				System.out.println("Number of allocated assets expiring on or before 2020-Sep-30: "+assetCount);
			else
				System.out.println("Sorry, report cannot be generated!");
						
			System.out.println();
			
			
			String[] desktopAssetIds = admin.generateAssetsReport(employees, 'D');

			System.out.println("All the allocated desktop assets");
			for (String assetId : desktopAssetIds) {
				if (assetId != null)
					System.out.println("\t"+assetId);
			}
			
			System.out.println();
			
			String[] laptopAssetIds = admin.generateAssetsReport(employees, 'L');
			
			System.out.println("All the allocated laptop assets");
			for (String assetId : laptopAssetIds) {
				if (assetId != null)
					System.out.println("\t"+assetId);
			}
			
			System.out.println();
			
		
			String[] voipAssetIds = admin.generateAssetsReport(employees, 'i');
			
			System.out.println("All the allocated VoIP assets");
			for (String assetId : voipAssetIds) {
				if (assetId != null)
					System.out.println("\t"+assetId);
			}
			
			System.out.println();
					
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
    }
}
