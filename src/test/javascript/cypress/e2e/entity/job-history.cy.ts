import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('JobHistory e2e test', () => {
  const jobHistoryPageUrl = '/job-history';
  const jobHistoryPageUrlPattern = new RegExp('/job-history(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const jobHistorySample = {};

  let jobHistory;
  // let department;
  // let job;
  // let employee;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/departments',
      body: {"departmentName":"fit how"},
    }).then(({ body }) => {
      department = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/jobs',
      body: {"jobTitle":"District Quality Technician","minSalary":25678,"maxSalary":22251},
    }).then(({ body }) => {
      job = body;
    });
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/employees',
      body: {"firstName":"Ruthie","lastName":"Strosin","email":"Jarred.Konopelski@hotmail.com","phoneNumber":"without blushing duh","hireDate":"2025-07-04T19:18:04.633Z","salary":25247,"commissionPct":29415},
    }).then(({ body }) => {
      employee = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/job-histories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/job-histories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/job-histories/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/departments', {
      statusCode: 200,
      body: [department],
    });

    cy.intercept('GET', '/api/jobs', {
      statusCode: 200,
      body: [job],
    });

    cy.intercept('GET', '/api/employees', {
      statusCode: 200,
      body: [employee],
    });

  });
   */

  afterEach(() => {
    if (jobHistory) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/job-histories/${jobHistory.id}`,
      }).then(() => {
        jobHistory = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (department) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/departments/${department.id}`,
      }).then(() => {
        department = undefined;
      });
    }
    if (job) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/jobs/${job.id}`,
      }).then(() => {
        job = undefined;
      });
    }
    if (employee) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/employees/${employee.id}`,
      }).then(() => {
        employee = undefined;
      });
    }
  });
   */

  it('JobHistories menu should load JobHistories page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('job-history');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('JobHistory').should('exist');
    cy.url().should('match', jobHistoryPageUrlPattern);
  });

  describe('JobHistory page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(jobHistoryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create JobHistory page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/job-history/new$'));
        cy.getEntityCreateUpdateHeading('JobHistory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', jobHistoryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/job-histories',
          body: {
            ...jobHistorySample,
            department: department,
            job: job,
            employee: employee,
          },
        }).then(({ body }) => {
          jobHistory = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/job-histories+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [jobHistory],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(jobHistoryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(jobHistoryPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response?.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details JobHistory page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('jobHistory');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', jobHistoryPageUrlPattern);
      });

      it('edit button click should load edit JobHistory page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('JobHistory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', jobHistoryPageUrlPattern);
      });

      it('edit button click should load edit JobHistory page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('JobHistory');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', jobHistoryPageUrlPattern);
      });

      // Reason: cannot create a required entity with relationship with required relationships.
      it.skip('last delete button click should delete instance of JobHistory', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('jobHistory').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', jobHistoryPageUrlPattern);

        jobHistory = undefined;
      });
    });
  });

  describe('new JobHistory page', () => {
    beforeEach(() => {
      cy.visit(jobHistoryPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('JobHistory');
    });

    // Reason: cannot create a required entity with relationship with required relationships.
    it.skip('should create an instance of JobHistory', () => {
      cy.get(`[data-cy="startDate"]`).type('2025-07-04T17:42');
      cy.get(`[data-cy="startDate"]`).blur();
      cy.get(`[data-cy="startDate"]`).should('have.value', '2025-07-04T17:42');

      cy.get(`[data-cy="endDate"]`).type('2025-07-04T23:15');
      cy.get(`[data-cy="endDate"]`).blur();
      cy.get(`[data-cy="endDate"]`).should('have.value', '2025-07-04T23:15');

      cy.get(`[data-cy="department"]`).select(1);
      cy.get(`[data-cy="job"]`).select(1);
      cy.get(`[data-cy="employee"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        jobHistory = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', jobHistoryPageUrlPattern);
    });
  });
});
