CSE 341 Final Project
Student name: Alibek Kaliyev
Email: alk224@lehigh.edu
------------------------------

This is the main README file for the project where I will describe the project and its components.

The program has 4 interfaces:
    1. Property Manager Interface
        - Recording visit data
        - Recording new lease data
            - Here you can also create a new tenant if it's not found
        - Recording the move out
    2. Tenant Interface
        - First, there is login/registration process for the tenant
            - If you are a new tenant, you can register yourself and set a password
            - If you are an existing tenant, you can login with your tenantID and password
                - All tenants who did not change a password have a default password of "qwerty12345"
            - All passwords are encrypted using SHA-256 and stored in the database
        - Then, after logging in, you can do the following:
            - View your personal data
            - View apartments you are currently renting
            - Update your personal data
            - View your leases
                - The program will show all leases that you have
                - Inside each lease, you can:
                    - Check payment status
                    - Make a rental payment if you haven't paid yet
                    - Add a person/pet
                    - Set a move out date
                    - View your other lease information
            - Update your password
    3. NUMA Manager Interface
        - Add a new Property
            - After answering a few questions, the program will create a new property
            - The program will also call PL/SQL procedures to populate the property with apartments and amenities
                - It is based on user's provided information
        - Edit a Property
        - View all properties
        - Raise a rent
            - But only if noone is renting the property
    4. Business Manager Interface
        - View statistics on general NUMA system
        - View statistics on apartments
        - View statistics on tenants

Things that I did differently from the ER Diagram:
    - 'Employer' relation. While it is still stored in the database, I decided not to involve employers in anything.
       In my opinion, it is not necessary.
    - I added amnt_id to the amenity table, since I understood that for each property I need to have a unique identifier for each amenity.
    - I added a 'password' column to the tenant table, since we need it for registration/login with SHA-256 encryption.

--------------------------------------------------------------------------------------------------------------------------------------------
Compilation and running:

You can compile and launch the program by simply running the run.sh file. It has a make command that will automatically compile the program.
Or you can separately compile with makefile using the 'make' command.

--------------------------------------------------------------------------------------------------------------------------------------------
Files and data generation:

All the data generation code is in 'datagen' folder. It was generated using random data from Mockaroo.
I also put the PL/SQL code in 'other/plsql' folder, and SQL code in 'other/sql' folder.
