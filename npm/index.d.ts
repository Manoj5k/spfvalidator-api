declare module '@apiverve/spfvalidator' {
  export interface spfvalidatorOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface spfvalidatorResponse {
    status: string;
    error: string | null;
    data: SPFValidatorData;
    code?: number;
  }


  interface SPFValidatorData {
      authorizedIPS:    AuthorizedIPS;
      dnsLookupsNum:    number;
      domainsExtracted: string[];
      elapsedMS:        number;
      hasIssues:        boolean;
      hasSPFRecord:     boolean;
      host:             string;
      ipPass:           boolean;
      macrosFound:      boolean;
      spfRecord:        string;
      spfRecordsList:   SPFRecordsList[];
      spfValid:         boolean;
  }
  
  interface AuthorizedIPS {
      ipv4: string[];
  }
  
  interface SPFRecordsList {
      authorizedIPS?: AuthorizedIPS;
      charsNum:       number;
      domains?:       string[];
      origin:         string;
      record:         string;
      useMacro:       boolean;
  }

  export default class spfvalidatorWrapper {
    constructor(options: spfvalidatorOptions);

    execute(callback: (error: any, data: spfvalidatorResponse | null) => void): Promise<spfvalidatorResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: spfvalidatorResponse | null) => void): Promise<spfvalidatorResponse>;
    execute(query?: Record<string, any>): Promise<spfvalidatorResponse>;
  }
}
