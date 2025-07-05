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

describe('Department e2e test', () => {
  const departmentPageUrl = '/department';
  const departmentPageUrlPattern = new RegExp('/department(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  // const departmentSample = {};

  let department;
  // let location;

  beforeEach(() => {
    cy.login(username, password);
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // create an instance at the required relationship entity:
    cy.authenticatedRequest({
      method: 'POST',
      url: '/api/locations',
      body: {"streetAddress":"settle fellow","postalCode":"decriminalize woot overreact","city":"Traceyshire","stateProvince":"gadzooks despite"},
    }).then(({ body }) => {
      location = body;
    });
  });
   */

  beforeEach(() => {
    cy.intercept('GET', '/api/departments+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/departments').as('postEntityRequest');
    cy.intercept('DELETE', '/api/departments/*').as('deleteEntityRequest');
  });

  /* Disabled due to incompatibility
  beforeEach(() => {
    // Simulate relationships api for better performance and reproducibility.
    cy.intercept('GET', '/api/locations', {
      statusCode: 200,
      body: [location],
    });

    cy.intercept('GET', '/api/employees', {
      statusCode: 200,
      body: [],
    });

    cy.intercept('GET', '/api/job-histories', {
      statusCode: 200,
      body: [],
    });

  });
   */

  afterEach(() => {
    if (department) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/departments/${department.id}`,
      }).then(() => {
        department = undefined;
      });
    }
  });

  /* Disabled due to incompatibility
  afterEach(() => {
    if (location) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/locations/${location.id}`,
      }).then(() => {
        location = undefined;
      });
    }
  });
   */

  it('Departments menu should load Departments page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('department');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Department').should('exist');
    cy.url().should('match', departmentPageUrlPattern);
  });

  describe('Department page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(departmentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Department page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/department/new$'));
        cy.getEntityCreateUpdateHeading('Department');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', departmentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      /* Disabled due to incompatibility
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/departments',
          body: {
            ...departmentSample,
            location: location,
          },
        }).then(({ body }) => {
          department = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/departments+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [department],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(departmentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });
       */

      beforeEach(function () {
        cy.visit(departmentPageUrl);

        cy.wait('@entitiesRequest').then(({ response }) => {
          if (response?.body.length === 0) {
            this.skip();
          }
        });
      });

      it('detail button click should load details Department page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('department');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', departmentPageUrlPattern);
      });

      it('edit button click should load edit Department page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Department');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', departmentPageUrlPattern);
      });

      it('edit button click should load edit Department page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Department');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', departmentPageUrlPattern);
      });

      // Reason: cannot create a required entity with relationship with required relationships.
      it.skip('last delete button click should delete instance of Department', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('department').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', departmentPageUrlPattern);

        department = undefined;
      });
    });
  });

  describe('new Department page', () => {
    beforeEach(() => {
      cy.visit(departmentPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Department');
    });

    // Reason: cannot create a required entity with relationship with required relationships.
    it.skip('should create an instance of Department', () => {
      cy.get(`[data-cy="departmentName"]`).type('morning');
      cy.get(`[data-cy="departmentName"]`).should('have.value', 'morning');

      cy.get(`[data-cy="location"]`).select(1);

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        department = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', departmentPageUrlPattern);
    });
  });
});
