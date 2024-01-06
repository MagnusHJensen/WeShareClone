/**
 * Import function triggers from their respective submodules:
 *
 * import {onCall} from "firebase-functions/v2/https";
 * import {onDocumentWritten} from "firebase-functions/v2/firestore";
 *
 * See a full list of supported triggers at https://firebase.google.com/docs/functions
 */

import {HttpsError, onCall} from "firebase-functions/v2/https";
import * as logger from "firebase-functions/logger";
import admin from "firebase-admin";

admin.initializeApp();

// Start writing functions
// https://firebase.google.com/docs/functions/typescript

export const sendNotification = onCall((request) => {
  const data = request.data as {
    title: string,
    body: string,
    tokens: string[]
  };
  const messages = data.tokens.map((token) => ({
    notification: {
      title: data.title,
      body: data.body,
    },
    token: token,
  }));

  return admin.messaging().sendEach(messages)
    .then((response) => {
      logger.info("Notification sent");
      return {
        success: true,
        response: response.responses,
      };
    })
    .catch((e) => {
      logger.error("Notification not sent", e.message);
      throw new HttpsError("unknown", e.message, e);
    });
});
