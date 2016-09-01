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
package org.microworld.mangopay.entities;

public enum EventType {
  DISPUTE_ACTION_REQUIRED,
  DISPUTE_CLOSED,
  DISPUTE_CREATED,
  DISPUTE_FURTHER_ACTION_REQUIRED,
  DISPUTE_SENT_TO_BANK,
  DISPUTE_SUBMITTED,
  DISPUTE_DOCUMENT_CREATED,
  DISPUTE_DOCUMENT_FAILED,
  DISPUTE_DOCUMENT_SUCCEEDED,
  DISPUTE_DOCUMENT_VALIDATION_ASKED,
  KYC_CREATED,
  KYC_FAILED,
  KYC_SUCCEEDED,
  KYC_VALIDATION_ASKED,
  MANDATE_ACTIVATED,
  MANDATE_CREATED,
  MANDATE_FAILED,
  MANDATE_SUBMITTED,
  PAYIN_NORMAL_CREATED,
  PAYIN_NORMAL_FAILED,
  PAYIN_NORMAL_SUCCEEDED,
  PAYIN_REFUND_CREATED,
  PAYIN_REFUND_FAILED,
  PAYIN_REFUND_SUCCEEDED,
  PAYIN_REPUDIATION_CREATED,
  PAYIN_REPUDIATION_FAILED,
  PAYIN_REPUDIATION_SUCCEEDED,
  PAYOUT_NORMAL_CREATED,
  PAYOUT_NORMAL_FAILED,
  PAYOUT_NORMAL_SUCCEEDED,
  PAYOUT_REFUND_CREATED,
  PAYOUT_REFUND_FAILED,
  PAYOUT_REFUND_SUCCEEDED,
  TRANSFER_NORMAL_CREATED,
  TRANSFER_NORMAL_FAILED,
  TRANSFER_NORMAL_SUCCEEDED,
  TRANSFER_REFUND_CREATED,
  TRANSFER_REFUND_FAILED,
  TRANSFER_REFUND_SUCCEEDED,
  TRANSFER_SETTLEMENT_CREATED,
  TRANSFER_SETTLEMENT_FAILED,
  TRANSFER_SETTLEMENT_SUCCEEDED;
}
