/**
 * Copyright © 2015 MicroWorld (contact@microworld.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.microworld.mangopay.entities.kyc;

public enum KycDocumentRefusedReason {
    DOCUMENT_UNREADABLE,
    DOCUMENT_NOT_ACCEPTED,
    DOCUMENT_HAS_EXPIRED,
    DOCUMENT_INCOMPLETE,
    DOCUMENT_MISSING,
    DOCUMENT_DO_NOT_MATCH_USER_DATA,
    DOCUMENT_DO_NOT_MATCH_ACCOUNT_DATA,
    DOCUMENT_FALSIFIED,
    UNDERAGE_PERSON,
    SPECIFIC_CASE,
}
